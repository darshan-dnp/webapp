packer {
  required_plugins {
    amazon = {
      version = ">= 1.2.6"
      source  = "github.com/hashicorp/amazon"
    }
  }
}

locals {
  timestamp = regex_replace(timestamp(), "[- TZ:]", "")
}

variable "ami_filter_name" {
  type    = string
  default = "debian-12-amd64*"
}

variable "ami_filter_root_device_type" {
  type    = string
  default = "ebs"
}

variable "ami_filter_virtualization_type" {
  type    = string
  default = "hvm"
}

source "amazon-ebs" "webapp" {
  profile   = "dev"
  ami_name  = "webapp-${local.timestamp}"
  ami_users = ["193615861989", "016757124451"]
  subnet_id = "subnet-0e170d96847faee27"
  source_ami_filter {
    filters = {
      name                = "${var.ami_filter_name}"
      root-device-type    = "${var.ami_filter_root_device_type}"
      virtualization-type = "${var.ami_filter_virtualization_type}"
    }
    most_recent = true
    owners      = ["amazon"]
  }
  instance_type = "t2.micro"
  region        = "us-east-1"
  ami_regions   = ["us-east-1"]
  ssh_username  = "admin"
  launch_block_device_mappings {
    device_name           = "/dev/xvda"
    volume_size           = 25
    volume_type           = "gp2"
    delete_on_termination = true
  }
}

build {
  sources = [
    "source.amazon-ebs.webapp"
  ]

  provisioner "file" {
    source      = "./target/webapp-0.0.1-SNAPSHOT.jar"
    destination = "~/webapp-0.0.1-SNAPSHOT.jar"
  }

  provisioner "file" {
    source      = "./opt/users.csv"
    destination = "~/users.csv"
  }

  provisioner "file" {
    source      = "./packer/webapp.service"
    destination = "~/webapp.service"
  }
  provisioner "file" {
    source      = "./packer/cloudwatch.service"
    destination = "~/cloudwatch.service"
  }

  provisioner "file" {
    source      = "./packer/CloudWatchConfig.json"
    destination = "~/CloudWatchConfig.json"
  }

  provisioner "shell" {
    script = "./packer/DependencySetup.sh"
  }
}