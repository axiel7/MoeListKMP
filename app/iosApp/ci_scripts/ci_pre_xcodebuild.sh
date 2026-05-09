#!/bin/sh

#  ci_pre_xcodebuild.sh
#  iosApp
#
#  Created by Axel Lopez on 09/05/2026.
#

# create private.properties file with the client secret id for Xcode Cloud builds
if [[ -n $MOELIST_CLIENT_SECRET_ID ]];
then
    touch private.properties
    echo """
    CLIENT_SECRET=${MOELIST_CLIENT_SECRET_ID}
    """ > private.properties
fi
