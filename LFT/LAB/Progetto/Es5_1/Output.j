.class public Output 
.super java/lang/Object

.method public <init>()V
 aload_0
 invokenonvirtual java/lang/Object/<init>()V
 return
.end method

.method public static print(I)V
 .limit stack 2
 getstatic java/lang/System/out Ljava/io/PrintStream;
 iload_0 
 invokestatic java/lang/Integer/toString(I)Ljava/lang/String;
 invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
 return
.end method

.method public static read()I
 .limit stack 3
 new java/util/Scanner
 dup
 getstatic java/lang/System/in Ljava/io/InputStream;
 invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
 invokevirtual java/util/Scanner/next()Ljava/lang/String;
 invokestatic java/lang/Integer.parseInt(Ljava/lang/String;)I
 ireturn
.end method

.method public static run()V
 .limit stack 1024
 .limit locals 256
 ldc 10
 istore 0
 iload 0
 istore 1
 iload 1
 istore 2
 iload 0
 invokestatic Output/print(I)V
 iload 1
 invokestatic Output/print(I)V
 iload 2
 invokestatic Output/print(I)V
 invokestatic Output/read()I
 istore 3
 invokestatic Output/read()I
 istore 4
 iload 3
 invokestatic Output/print(I)V
 iload 4
 invokestatic Output/print(I)V
 ldc 1
 invokestatic Output/print(I)V
 ldc 2
 ldc 3
 iadd 
 ldc 4
 iadd 
 invokestatic Output/print(I)V
 iload 3
 iload 4
 if_icmpgt L1
 goto L2
L1:
 iload 3
 invokestatic Output/print(I)V
 goto L3
L2:
 iload 4
 invokestatic Output/print(I)V
L3:
L6:
 iload 3
 ldc 0
 if_icmpgt L4
 goto L5
L4:
 iload 3
 ldc 1
 isub 
 istore 3
 goto L6
L5:
L0:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

