#!/bin/sh

fatal()
{
  echo "fatal: $1" 1>&2
  exit 1
}

info()
{
  echo "info: $1" 1>&2
}

CURRENT_DIR=`pwd` ||
  fatal "could not retrieve current directory"

rm -rf doc-out ||
  fatal "could not remove doc-out"

xmllint \
  --noout \
  --xinclude \
  --schema ext/structural-0.1.0/structural-01.xsd \
  doc/documentation.xml ||
  fatal "could not validate document"

mkdir doc-out ||
  fatal "could not create output directory"
cd doc-out ||
  fatal "could not switch to output directory"

saxon \
  -xi:on \
  -xsl:../ext/structural-0.1.0/structural-01-standalone-x20.xsl \
  -s:../doc/documentation.xml ||
  fatal "could not generate documentation"

cp ../ext/structural-0.1.0/*.css . || fatal "could not copy CSS"
cp ../doc/*.css .                  || fatal "could not copy CSS"
mv ../javadoc .                    || fatal "coult not insert javadoc"

cd "${CURRENT_DIR}" ||
  fatal "could not restore directory"

VERSION=`head -n 1 README-VERSION.txt | sed 's/ /-doc-/g'` ||
  fatal "could not retrieve version"

mv doc-out "${VERSION}" ||
  fatal "could not rename output directory"

