# APIRePublisher-Java-Client
```
niran:niran$ java -jar APIRePublish.jar 
==============================================
============ Starting Java Client ============
==============================================
Enter management URL (Ex:localhost:9443) : localhost:9443
Enter Gateway URL (Ex:localhost:8243) : localhost:8243
Enter Tenant Admin Username (Ex:admin@tenant.com) : niran@test.com
Enter Tenant Admin Password : admin
Enter SP Name (Ex:rest_api_publisher_client) : rest_api_publisher_client_4
============ Starting Client Registration ============
Base64 Encoded Credentials: bmlyYW5AdGVzdC5jb206YWRtaW4=
Client Registration Call Response Code :200
DCR Endpoint Response: {"clientId":"JmmLovC0HMZalnn125nzBylEwG0a","clientName":"niran_rest_api_publisher_client_4","callBackURL":"www.google.lk","clientSecret":"eL4Y1wgxMUYPaqgCrgEBRvD3ql8a","isSaasApplication":true,"appOwner":"niran@test.com","jsonString":"{\"grant_types\":\"password refresh_token\",\"redirect_uris\":\"www.google.lk\",\"client_name\":\"niran_rest_api_publisher_client_4\"}","jsonAppAttribute":"{}","tokenType":null}
Client ID : JmmLovC0HMZalnn125nzBylEwG0a
Client Secret : eL4Y1wgxMUYPaqgCrgEBRvD3ql8a
Client Credentials : Sm1tTG92QzBITVphbG5uMTI1bnpCeWxFd0cwYTplTDRZMXdneE1VWVBhcWdDcmdFQlJ2RDNxbDhh
============ Client Registration Completed ============
============ Generating Access Token ============
Token Call Response Code :200
Token Endpoint Response: {"access_token":"15c0d8b9-bee8-3fa4-a5f3-f49b51ac497f","refresh_token":"0a3c7e6d-211c-3cea-be9b-ebac21a8c7f4","scope":"apim:api_publish apim:api_view","token_type":"Bearer","expires_in":3600}
Access Token : 15c0d8b9-bee8-3fa4-a5f3-f49b51ac497f
============ Access Token Generated ============
============ Retrieving List of APIs ============
API_LIST Call Response Code :200
API_LIST Endpoint Response: {"count":2,"next":"","previous":"","list":[{"id":"0869472c-4ab3-4e8c-a210-948bdaadb5e6","name":"t2api1","description":null,"context":"/t/test.com/t2api1","version":"v1","provider":"niran@test.com","status":"PUBLISHED","thumbnailUri":null},{"id":"cb7148ca-19d9-433d-b5e4-ccbe158a6572","name":"t2api2","description":null,"context":"/t/test.com/t2api2","version":"v1","provider":"niran@test.com","status":"PUBLISHED","thumbnailUri":null}],"pagination":{"total":2,"offset":0,"limit":25}}
============ API List Received ============
============ API : t2api1 : Successfully Changed the State to Demote%20to%20Created ============
============ API : t2api1 : Successfully Changed the State to Publish ============
============ API : t2api2 : Successfully Changed the State to Demote%20to%20Created ============
============ API : t2api2 : Successfully Changed the State to Publish ============
==============================================
============ Java Client Completed ===========
==============================================
```
