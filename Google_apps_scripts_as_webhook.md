# Google Apps Scripts as WebHook

## Making the presentations
I'm sure that some of you haven't missed the "Google App Script".
> Google Apps Script is a scripting language based on JavaScript that lets you
> do new and cool things with Google Apps like Docs, Sheets, and Forms. There's
> nothing to install — we give you a code editor right in your browser, and your
> scripts run on Google's servers. [1]

I have to admit that I didn't see the awesomeness of this system until a couple
of days ago. At least it is really heplfull because it can of course do "cool
things with Google Apps" but it can also be exposed as webapp and send Http
requests.. So, yes, it can be used as a WebHook pipe.

> A WebHook is an HTTP callback: an HTTP POST that occurs when something happens
> ; a simple event-notification via HTTP POST.
> ..
> A Pipe happens when your WebHook not oinly receives real-time data, but goes
> on to do something new and meaningful with it, triggering actions unrelated to
> the original events. [2]


## Hacking Google Apps Script
Some of my projects are hosted on Bitbucket and built by Codeship. Both works
great but there is no out of the box "build status" integration between Codeship
and Bitbucket. There are some tentatives but they are not natively integrated
into the Bitbcuket UI.

That's why I decided to build my own. I have used Google Apps Script to create a
script who will receive the Codeship POST when a build status changes and call
the Bitbcuket API to update the build status.

The idea is quite simple and the documentation is clear on both sides [3][4] and
fortunately Bitbucket API is usable with Basic auth (thanks god). But debugging
a Google Apps Script is not so easy and I took me a while to guess why this
"#$!&§" script was not working [5] . But at the end it is quite simple.
![](Google_apps_scripts_as_webhook.png)

The script is available as Gist:
[gervaisb/CodeShip-Bitbucket-Build_status.js](https://gist.github.com/gervaisb/206a3441b2ed454e81d5)


________________________________________________________________________________
Links :

- [1] Google Apps Script :
   + https://developers.google.com/apps-script
   + https://developers.google.com/apps-script/guides/web
- [2] WebHooks.org :
   + https://webhooks.pbworks.com/w/page/13385124/FrontPage
- [3] Bitbbucket REST API Version 2 :
   + https://confluence.atlassian.com/bitbucket/version-2-423626329.html
- [4] Codeship WebHooks :
   + https://codeship.com/documentation/integrations/webhooks/
- [5] Effective way to debug a Google Apps Script Web App on Stackoverflow :
   +  http://stackoverflow.com/questions/11493643/effective-way-to-debug-a-google-apps-script-web-app

Tags : _[Codeship][Bitbucket][Google Apps Script][Integration]_
