#!/bin/sh

#  ci_pre_xcodebuild.sh
#  iosApp
#
#  Created by Axel Lopez on 09/05/2026.
#

# create private.properties file with the client secret id for Xcode Cloud builds
if [[ -n $MOELIST_CLIENT_SECRET_ID ]];
then
    touch /Volumes/workspace/repository/private.properties
    echo """
    CLIENT_SECRET=${MOELIST_CLIENT_SECRET_ID}
    """ > /Volumes/workspace/repository/private.properties
fi
