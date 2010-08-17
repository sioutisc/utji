# ensure jack.jar is locared in your classpath already
# or simply make a symbolic link to jack.jar in the lib directory
for file in `ls ./lib`
do
  export CLASSPATH=$CLASSPATH:./lib/$file
done
export CLASSPATH=$CLASSPATH:./dist/lib/utji.jar
