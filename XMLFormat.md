The format of the XML closely mirrors the layout of the source spreadsheet. Here is an example:

```
<workbook> 
  <sheet number="0"> 
    <name><![CDATA[Index]]></name> 
    <row number="0"> 
    </row> 
    <row number="1"> 
      <col number="1"><![CDATA[Time Series Workbook]]></col> 
    </row> 
    ...
    <row number="7"> 
      <col number="0"><![CDATA[Related Information:]]></col> 
      <col number="1"><![CDATA[Summary Publication]]></col> 
      <col number="2"><![CDATA[Explanatory Notes]]></col> 
      <col number="3"><![CDATA[Inquiries]]></col> 
    </row> 
    ...
  </sheet>
  <dataSource>
    <![CDATA[http://www.ausstats.abs.gov.au....]]>
  </dataSource> 
</workbook> 
```
Note that:
  * element numbers are zero-based
  * columns are contained by rows
  * the dataSource element provides a link to the source .xls file on the ABS website