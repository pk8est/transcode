@echo off

::协议文件路径, 最后不要跟“\”符号
set SOURCE_FOLDER=..\..\java\com\pkest\netty\proto

::Java编译器路径
set JAVA_COMPILER_PATH=..\tools\protoc.exe
::Java文件生成路径, 最后不要跟“\”符号
set JAVA_TARGET_PATH=..\..\java

::删除之前创建的文件
::del %JAVA_TARGET_PATH%\*.* /f /s /q

::遍历所有文件
for /f "delims=" %%i in ('dir /b "%SOURCE_FOLDER%\*.proto"') do (
       
    ::生成 Java 代码
    echo %JAVA_COMPILER_PATH% --proto_path=%SOURCE_FOLDER% --java_out=%JAVA_TARGET_PATH% %SOURCE_FOLDER%\%%i
    %JAVA_COMPILER_PATH% --proto_path=%SOURCE_FOLDER% --java_out=%JAVA_TARGET_PATH% %SOURCE_FOLDER%\%%i
    
)

echo done!

pause