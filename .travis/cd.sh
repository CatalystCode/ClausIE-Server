#!/usr/bin/env bash

set -eo pipefail

if [ -z "$TRAVIS_TAG" ]; then
  echo "Build is not a release, skipping CD" >&2; exit 0
fi

if [ -z "$DOCKER_USERNAME" ] || [ -z "$DOCKER_PASSWORD" ]; then
  echo "No docker credentials configured, unable to publish builds" >&2; exit 1
fi

tag="$DOCKER_USERNAME/clausieserver:$TRAVIS_TAG"
context="$(dirname $0)/.."

docker build --tag "$tag" "$context"
docker login --username="$DOCKER_USERNAME" --password="$DOCKER_PASSWORD"
docker push "$tag"
