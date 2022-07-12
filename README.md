# APIRePublisher-Java-Client
Without Pre-defined list of APIs.
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
With Pre-defined list of APIs.
```
niran$ java -jar APIRePublish.jar 
==============================================
============ Starting Java Client ============
==============================================
Enter management URL (Ex:localhost:9443) : localhost:9443
Enter Tenant Admin Username (Ex:admin@tenant.com) : niran@test.com
Enter Tenant Admin Password : admin
Enter SP Name (Ex:rest_api_publisher_client) : rest_api_publisher_client
List of APIs From Config File (yes/no) : yes
Number of APIs in the list : 3
============ Starting Client Registration ============
Base64 Encoded Credentials: bmlyYW5AdGVzdC5jb206YWRtaW4=
Client Registration Call Response Code :200
DCR Endpoint Response: {"clientId":"a5E6oIvHPBSezoieESwXoMzZskAa","clientName":"niran_rest_api_publisher_client","callBackURL":"www.google.lk","clientSecret":"5lF6L9j8jS6WLvZVFTEoRS9YUjoa","isSaasApplication":true,"appOwner":null,"jsonString":"{\"grant_types\":\"password refresh_token\"}","jsonAppAttribute":"{}","tokenType":null}
Client ID : a5E6oIvHPBSezoieESwXoMzZskAa
Client Secret : 5lF6L9j8jS6WLvZVFTEoRS9YUjoa
Client Credentials : YTVFNm9JdkhQQlNlem9pZUVTd1hvTXpac2tBYTo1bEY2TDlqOGpTNldMdlpWRlRFb1JTOVlVam9h
============ Client Registration Completed ============
============ Generating Access Token ============
Token Call Response Code :200
Token Endpoint Response: {"access_token":"5567528d-cb29-3615-9a37-1fc1441f1c31","refresh_token":"68541ba0-5a5e-371f-896b-1210f0d3e7fd","scope":"apim:api_publish apim:api_view","token_type":"Bearer","expires_in":1128}
Access Token : 5567528d-cb29-3615-9a37-1fc1441f1c31
============ Access Token Generated ============
============ Retrieving t2api3-v1: API Details ============
API_DETAILS Call Response Code :200
API_DETAILS Endpoint Response: {"count":1,"next":"","previous":"","list":[{"id":"1913f7e1-e442-4a6c-94f2-2b404a46c026","name":"t2api3","description":null,"context":"/t/test.com/t2api3","version":"v1","provider":"niran@test.com","status":"PUBLISHED","thumbnailUri":null}],"pagination":{"total":1,"offset":0,"limit":25}}
============ API Details Received ============
============ Retrieving t2api2-v1: API Details ============
API_DETAILS Call Response Code :200
API_DETAILS Endpoint Response: {"count":1,"next":"","previous":"","list":[{"id":"cb7148ca-19d9-433d-b5e4-ccbe158a6572","name":"t2api2","description":null,"context":"/t/test.com/t2api2","version":"v1","provider":"niran@test.com","status":"PUBLISHED","thumbnailUri":null}],"pagination":{"total":1,"offset":0,"limit":25}}
============ API Details Received ============
============ Retrieving t2api2-v2: API Details ============
API_DETAILS Call Response Code :200
API_DETAILS Endpoint Response: {"count":1,"next":"","previous":"","list":[{"id":"68ff17d1-ea25-442c-a118-726eb6a61728","name":"t2api2","description":null,"context":"/t/test.com/t2api2","version":"v2","provider":"niran@test.com","status":"PUBLISHED","thumbnailUri":null}],"pagination":{"total":1,"offset":0,"limit":25}}
============ API Details Received ============
============ API : t2api3(1913f7e1-e442-4a6c-94f2-2b404a46c026) : Successfully Changed the State to Created State ============
============ API : t2api3(1913f7e1-e442-4a6c-94f2-2b404a46c026) : Successfully Changed the State to Publish State ============
============ API : t2api2(cb7148ca-19d9-433d-b5e4-ccbe158a6572) : Successfully Changed the State to Created State ============
============ API : t2api2(cb7148ca-19d9-433d-b5e4-ccbe158a6572) : Successfully Changed the State to Publish State ============
============ API : t2api2(68ff17d1-ea25-442c-a118-726eb6a61728) : Successfully Changed the State to Created State ============
============ API : t2api2(68ff17d1-ea25-442c-a118-726eb6a61728) : Successfully Changed the State to Publish State ============
==============================================
============ Java Client Completed ===========
==============================================
```