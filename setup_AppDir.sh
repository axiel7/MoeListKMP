#!/bin/sh

rm -rf AppDir || true
mv output AppDir
mkdir -p AppDir/usr/share/icons/hicolor/scalable/apps/
mkdir -p AppDir/usr/share/applications
mv AppDir/bin AppDir/usr/
mv AppDir/lib AppDir/usr/
ln -s usr/bin/moelist AppDir/AppRun
cp com.axiel7.moelist.desktop AppDir/
cp com.axiel7.moelist.desktop AppDir/usr/share/applications
cp AppDir/usr/lib/app/icon.svg AppDir/usr/share/icons/hicolor/scalable/apps/moelist.svg
cp AppDir/usr/lib/app/icon.svg AppDir/moelist.svg
