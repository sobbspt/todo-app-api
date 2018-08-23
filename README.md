# todo-ui

Simple To-Do application API build with LINE messaging API. This must be used with my todo-app-ui.

## Environment variables
| Name                          | Value                                                             |
| ----------------------------- |-------------------------------------------------------------------|
| DEV_TOOL_ENABLED              | true/false disabled on production                                 |
| MONGO_HOST                    | mongo host url                                                    |
| MONGO_DB_NAME                 | mongo database name                                               |
| MONGO_USER                    | mongo database user                                               |
| MONGO_PASSWORD                | mongo database password                                           |
| LINE_CHANNEL_TOKEN            | token of LINE bot channel                                         |
| LINE_CHANNEL_SECRET           | secret of LINE bot channel                                        |
| LINE_GET_ACCESS_TOKEN_URL     | https://api.line.me/v2/oauth/accessToken                          |
| LINE_CLIENT_ID                | client ID of LINE web login                                       |
| LINE_CLIENT_SECRET            | secret of LINE web login                                          |
| LINE_GRANT_TYPE               | grant type of LINE web login (authorization_code)                 |
| LINE_REDIRECT_URI             | url for web frontend to receive callback after user logged in     |
| PORT                          | your preferred port                                               |

## Test
```
gradlew test
```

## Project build
```
gradlew build
```

### Run
```
gradlew bootRun
```
