absxml is a simple Google AppEngine service that provides time series data from the Australian Bureau of Statistics in XML format. The ABS publishes the vast majority of its data under a Creative Commons licence, however for legacy reasons it uses Excel as the delivery mechanism. This makes it difficult to use programatically.

absxml works as an intermediary between your application and the ABS website, converting the publications to XML. It also ensures that the latest version of the publication is used ([caching aside](http://code.google.com/p/absxml/wiki/Caching)). To call the service you can manually [construct the URL](http://code.google.com/p/absxml/wiki/ConstructingTheURL) or use the simple form on the [AppEngine page](http://absxml.appspot.com) that will construct it for you.

See the issues tab for known shortcomings and planned improvements. See the Wiki tab for some very limited documentation.

**Disclaimer**
This is currently very much in early beta - it's probably very broken.
This site is in no way associated with or approved by the ABS.
