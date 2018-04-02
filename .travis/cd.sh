#!/usr/bin/env bash

set -eo pipefail

if [ -z "$TRAVIS_TAG" ]; then
  echo "Build is not a release, skipping CD" >&2; exit 0
fi

if [ -z "$DOCKER_USERNAME" ] || [ -z "$DOCKER_PASSWORD" ]; then
  echo "No docker credentials configured, unable to publish builds" >&2; exit 1
fi

current_tag="$DOCKER_USERNAME/clausieserver:$TRAVIS_TAG"
latest_tag="$DOCKER_USERNAME/clausieserver:latest"
context="$(dirname $0)/.."

docker build --tag "$current_tag" "$context"
docker login --username="$DOCKER_USERNAME" --password="$DOCKER_PASSWORD"
docker push "$current_tag"

docker tag "$current_tag" "$latest_tag"
docker push "$latest_tag"
