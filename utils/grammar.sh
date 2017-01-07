#!/usr/bin/env bash
sed -e ':again' -e N -e '$!b again' -e ':b' -e "s/{}/:|/g" -e "s/\\'{\\'/:+/g" -e "s/\\'}\\'/:-/g" -e "s/{[^{}]*}//g" -e "s/{}/:=/g" -e 't b' -e "s/:+/\\'{\\'/g" -e "s/:-/\\'}\\'/g" -e "s/:|/{}/g" -e "s/returns \\[[a-zA-Z ]*\\]//g" -e "s/[a-z][a-z0-9]*=//g" $1
