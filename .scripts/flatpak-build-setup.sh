#!/bin/bash

# Flatpak build setup script
# This script contains the build commands extracted from the flatpak YAML configuration

set -e

echo "Setting up for Flatpak build..."

# Update Gradle wrapper distribution URL
sed -i s/distributionUrl.*/distributionUrl=gradle-bin.zip/ gradle/wrapper/gradle-wrapper.properties

# Remove foojay toolchain resolver (not needed in offline build)
sed -i '/org.gradle.toolchains.foojay-resolver-convention/d' settings.gradle.kts

# Disable toolchain auto-provisioning
echo "org.gradle.java.installations.auto-detect=false" >> gradle.properties
echo "org.gradle.java.installations.auto-download=false" >> gradle.properties

echo "Flatpak build setup completed!"