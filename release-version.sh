#!/bin/sh

# Check the input params
if [ $# -ne 2 ]; then
	echo "Usage:"
	echo ""
	echo "$0 <release-version> <next-dev-version>"
	echo ""
	echo "- release-version - The version which should be tagged (e.g. '1.0')"
	echo "- next-dev-version - Next version which will be used for further development (e.g. '1.1-SNAPSHOT')"
	echo ""
	exit
fi

# Stop the script on first error, output commands before executing
set -e -x

# Sync with remote to assure we have no conflict
git fetch
git checkout master && git merge --ff-only
git checkout develop && git merge --ff-only

# Build develop
git checkout develop
mvn clean verify

# Create release branch
git branch release/$1 develop

# Maven release version
git checkout release/$1
mvn versions:set -DnewVersion="$1" -DgenerateBackupPoms=false
git add "*pom.xml"
git commit --gpg-sign -m "release/$1 Maven project version set to $1"

# Finish the release branch
git checkout master
git merge --gpg-sign -m "Merge branch 'release/$1' into master" --no-ff release/$1
git branch -d release/$1

# Build new production artifacts
mvn clean deploy -P release

# Tag the new relase version
git tag --sign -m "Version $1" -a v$1

# Merge master back to develop branch
git checkout develop
git merge --gpg-sign -m "Merge branch 'master' with version '$1' into develop" --no-ff master

# Maven next development version
mvn versions:set -DnewVersion="$2" -DgenerateBackupPoms=false
git add "*pom.xml"
git commit --gpg-sign -m "release/$1 Development continues on version $2"

# Push the changes to the 'origin' repo
git push --tags origin master develop
