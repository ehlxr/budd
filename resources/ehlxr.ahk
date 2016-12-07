/*
# 号代表 Win 键；
! 号代表 Alt 键；
^ 号代表 Ctrl 键；
+ 号代表 shift 键；
:: 号(两个英文冒号)起分隔作用；
run，非常常用 的 AHK 命令之一;
; 号代表 注释后面一行内容；
*/

; 一键打开网页
#0::Run http://ehlxr.me
#9::Run http://www.baidu.com

^#!Numpad0::run,D:\works\Sublime Text 3\sublime_text.exe
^#!Numpad9::run,D:\works\IntelliJ IDEA 2016.3\bin\idea64.exe
^!f::run,D:\Program Files\Everything\Everything.exe

/*
一键拷贝文件路径
*/
^+c::
; null=
send ^c
sleep,200
clipboard=%clipboard% ;%null%
tooltip,%clipboard%
sleep,500
tooltip,
return

/*
改掉大写键为Enter
用Alt+CapsLock来组合实现大写
*/
;replace CapsLock to LeftEnter; CapsLock = Alt CapsLock
$CapsLock::Enter
LAlt & Capslock::SetCapsLockState, % GetKeyState("CapsLock", "T") ? "Off" : "On"
!u::Send ^c !{tab} ^v

;缩写快速打出常用语
::.m::ehlxr.me@gmail.com
::.l::lixiangrong


/*
取得鼠标所在光标处颜色色值到剪切版中
Ctrl+Win+c
*/
^#c::
MouseGetPos, mouseX, mouseY
; 获得鼠标所在坐标，把鼠标的 X 坐标赋值给变量 mouseX ，同理 mouseY
PixelGetColor, color, %mouseX%, %mouseY%, RGB
; 调用 PixelGetColor 函数，获得鼠标所在坐标的 RGB 值，并赋值给 color
StringRight color,color,6
; 截取 color（第二个 color）右边的6个字符，因为获得的值是这样的：#RRGGBB，一般我们只需要 RRGGBB 部分。把截取到的值再赋给 color（第一个 color）。
clipboard = %color%
; 把 color 的值发送到剪贴板
return


/*
 短脚本 — 用鼠标切换任务
功能：
鼠标右键+滚轮下：快速切换至前一个任务（即，在最近两个任务间切换，因为当切换至下一个任务时，当前任务自动成为下一个任务）
鼠标右键+滚轮上：依次切换至最近的第i个任务，i从3开始（第2个使用以上的前一个任务），按下保持鼠标右键，向上滚动，每滚一次i+1。

RButton & WheelDown::AltTab
RButton & WheelUp::f20147310()
f20147310(){
    static i:=2
    if (A_Priorkey="WheelUp")    ;中继
        i++
    else    ;复位
        i:=2
    Send !{tab %i%}
}
RButton::Send {RButton}
*/

; Win+Esc 关闭当前窗口
#Esc::Send !{F4}