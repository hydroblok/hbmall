#!/usr/bin/env bash

echo "Home path: $HOME"
# Make sure we have gcloud installed in travis env
if [ ! -d "$HOME/google-cloud-sdk/bin" ]; then
  rm -rf "$HOME/google-cloud-sdk"
  curl https://sdk.cloud.google.com | bash > /dev/null
fi

# Promote gcloud to PATH top priority (prevent using old version fromt travis)
source $HOME/google-cloud-sdk/path.bash.inc

# Make sure kubectl is updated to latest version
gcloud components update kubectl

make -f config/Makefile gauth build push deploy