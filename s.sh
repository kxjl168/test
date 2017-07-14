p=~/kxjl/test2

p2=~/kxjl/test22
if [ -x "${p}" ]; then
 echo 'exist' ${p}
else 
 echo 'not exist ' ${p}
fi

echo ${p2} 'done!'
