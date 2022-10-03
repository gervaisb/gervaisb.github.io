
# My ideal setup

> Like in many other professions, we have some favorite tools, some loved by 
> many peers some only by us. But more than the tools, we also have some 
> prefered way to use them.

I am a backend developer with a strong experience on Java and Scala. My setup 
is then focused on those langages but some tools or configuration may be 
usefull for others.

## A unix based Os

When I was forced to work on a Windows workstation, I installed VirtualBox with 
a light linux distribution. Luckily I was then able to choose my Os an worked a 
lot with Pop! Os or other Ubuntu based distros. Then I got my first MacBook. 
While I cannot say if I refer MacOs or "Linux", I am definitely sold to those 
Unix based OS. Being a fan of the command line, I need an OS that give me a 
total control from my terminal. 

## IntelliJ Idea

NetBeans and Eclipse are two famous Java ides but, in my eyes, none of thme can beat IntelliJ. My first actions are to disable all the plugins that I do not care (Unused build tools, databases and deployment, JavaScript and Java frameworks, Version controls, etc..).

### Appearance

I keep the light theme for the ide but use Darcula for the editor and set the View mode of all tools window as _Undock_ so that they can slide over the editor and never consume the display area. As the tools are light it is easy to identify the different regions on my display. To gain more space I use the distraction free mode and create two vertical panes. I never use tabs nor the project view to open files but use the "Recent files", "Go to class" and "Got to file" to quickly navigate between my sources.

### Plugins

To improve my own experience I rely on some great plugins:

#### Rainbow Brackets

This plugin just use a different color for each pair of matching brackets. This give a better understanding when you have a block with many brackets.

https://plugins.jetbrains.com/plugin/index?xmlId=izhangzhihao.rainbow.brackets

#### Key Promoter X

This annoying plugin display a notification when you use your mouse instead of a keyboard shortcut to execute an action. As I am trying to keep my fingers on my keyboard this one was really helpfull. I concentrate myself on the editor actions and a most used tools and commands. For the less frequent actions I keep using the mouse and tell the plugin to ignore them.

https://plugins.jetbrains.com/plugin/index?xmlId=Key%20Promoter%20X

#### AceJump

The most recent addition in my toolbox, not yet used as it should but this one helps me to navigate quickly inside the opened source files. This plugin leyt me press `Control + ;` and then type one or more character then quickly jump to the matched lines and it can jump accros panes so I can easily navigate from the test case to the implementation method.

https://plugins.jetbrains.com/plugin/index?xmlId=AceJump

### Keymap

I try to keep the default keymap for my platform. However I create some shortcuts to split and navigate between panes. But, as said previously, I concentrate on the editor shortcuts those of the most used tools. 

Once I became used to the _tmux_ keymap to manage the panes I applied the same in IntellIj.

    + `Ctrl+B, z`  To enter zen mode
    + `Ctrl+B, %`  Split vertically
    + `Ctrl+B, "`  Split Horizontally

The difference is that `Meta + Arrow` does not select a pane since it is already bound to word navigation. But I use the "recent files" dialog or _AceJump_ to quickly switch to one pane or


## A terminal with tiling and a good shell

On Linux I use _Alacritty_ but it has some flaws on MacOS so I switch to _iTerm_. Anyway, I don't use their feaures anymore I prefer to use _tmux_ with _zsh_.

### tmux

I use tmux for his persistent sessions, multi window and panes. It may look a bit hard at the beginning but it quickly became one of the first thing I install.

I am not an advanced user, I use it mainly to split panes and manage some sessions. So I have kept the default keymap, I have onoy enabled the mouse support and bound the "Meta"-Arrow to move between panes.

    set -g mouse on
   
    # Switch panes using Option-arrow
    bind -n M-Left select-pane -L
    bind -n M-Right select-pane -R
    bind -n M-Up select-pane -U
    bind -n M-Down select-pane -D

### Zsh

Zsh is another shell interperter with a list of awesome plugins. 

I use it with "Ho my Zsh" that provide sane defaults and themes. I use the lambda theme with Darcula color scheme

### exa

Exa is a better ls, I alias the `ls` command to it. Exa is not 100% compatible  the ls arguments but, for the day to day it is good enough. 

### bat

Bat is a better cat with syntax coloration and manu capabilities like like numbers. It has some annoying default that prints the line numbers and file header but they can be removed easily.

### sdkman

SdkMan let me manage my Java and Scala sdks but also Maven, Sbt, Gradle and a lot more tools. A simple command let me swictch my Jdk for the current session.

### httpie

I discovered _Httpie_ a few years ago and it is now part of my favorits tools. It is another Http client, but easier than Curl and with good smart defaults and easy to use syntax. 

### VSCodium

VSCodium is a fork of VSCode without the Microsoft spyies in it. It is fully compatible with VSCode bit with a bit more privacy. It is my defacto editor for many kind of files

--------------------------------------------------------------------------------
- [x] HttpIe
- [x] Sdkman
- [x] Idea
- [ ] VSCodium
- [x] Tmux
- [x] Zsh
- [x] Brew
- [x] Bat et Exa