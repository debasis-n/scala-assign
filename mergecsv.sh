#!/bin/bash

if [[ $# -eq 1 ]] ; then
    echo 'usage:'
    echo './mergecsv.sh <pattern> <outputfile.csv>'
    exit 1
fi

output_file=$2

i=0

files=$(ls "$1"*".csv" )
echo $files
for filename in $files; do
  echo $i
  if [[ $i -eq 0 ]] ; then
    # copy csv headers from first file
    echo "first file to copy header as well"
    head -1 $filename > $output_file
  fi
  echo $i "copy csv without header"
  # copy csv without headers from other files
  tail -n +2 $filename >> $output_file
  i=$(( $i + 1 ))
done
