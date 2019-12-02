通过终端查看语法树

1. 进入到src/main/antlr/com.antlrDemo.util 下
```
antlr *.g4
javac *c
grun Java compilationUnit -gui FILE_NAME (待测试语法树的文件）
```

CPD 测试

进入到 java/resource 下，
```$xslt
cpd --minimum-tokens 100 --files test.c 

```