call runcrud
if "%ERRORLEVEL%" == "0" goto webBrowser
goto fail

:webBrowser
"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were errors with runcrud.bat

:end
echo.
echo Everything is done.