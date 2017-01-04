sed -e ':again' -e N -e '$!b again' -e ':b' -e "s/\\'{\\'/:+/g" -e "s/\\'}\\'/:-/g" -e "s/{[^{}]*}//g" -e 't b' -e "s/:+/\\'{\\'/g" -e "s/:-/\\'}\\'/g" CFDG.g4 >grammar.txt
