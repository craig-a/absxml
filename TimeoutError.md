Google App Engine has some limitations to ensure that apps don't put too much load on the (free) infrastructure. The two most relevant to absxml are:
  * 10 seconds to fetch data via URL
  * 30 seconds total execution time per request

What this means in practice is that the service has only 10 seconds to get the Excel file from the ABS website, and a further 20 or so to convert it to XML. This is only an issue when the XML has not already been cached.

In the vast majority of circumstances fetching the Excel spreadsheet takes significantly longer than the XML conversion, so in reality the 30 second limit should never be reached.

It is quite possible however that, under load, the ABS website will not be able to serve the larger spreadsheets fast enough, which will result in a timeout error. If this occurs, wait a while and try again. It is possible that some very large spreadsheets will never be served within the time limit. Unfortunately, unless Google increases the timeout there is nothing that can be done.