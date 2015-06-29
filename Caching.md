absxml keeps a local cache of the XML it produces, for two main reasons:
  * to improve performance on the servlet
  * to reduce the downloads performed by the servlet (benefiting the servlet host and the ABS website

Caching is currently very simple - when a sheet is requested, if it is not in the cache the spreadsheet is fetched and the whole workbook is converted to XML and cached.

That is, the cache is populated on demand and never flushed. There are three main take-away points from this:
  * the first user to request a specific spreadsheet wears the performance hit of creating the XML
  * subsequent requests for any sheet from a spreadsheet are very fast
  * if a publication is cached, it will not get newly released data from the ABS website until the servlet is reloaded

As per the Issues page, there are several enhancements planned for caching that should address these issues