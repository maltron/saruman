//
//  Default.h
//  saruman
//
//  Created by Mauricio Leal on 3/16/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#define IDENTIFIER_SERVER  @"SARUMAN_SERVER"
#define IDENTIFIER_PORT    @"SARUMAN_PORT"
#define IDENTIFER_CURRENT_USER @"CURRENT_USER"
#define IDENTIFER_CURRENT_ROLE @"CURRENT_ROLE"

#define ROLE_ADMIN         @"admin"
#define ROLE_USER          @"user"

#define DEFAULT_SERVER     @"192.168.1.123"
#define DEFAULT_PORT       8080
#define DEFAULT_PROTOCOL   @"http://"

#define SERVER_CONTEXT       @"/saruman"
#define SERVER_REST          @"/rest"
#define SERVER_REST_USER     @"/rest/user"
#define SERVER_REST_USERNAME @"/rest/user/username"
#define SERVER_REST_ROLE     @"/rest/role"
#define SERVER_REST_COUNTRY  @"/rest/country"
#define SERVER_REST_REGION   @"/rest/region"
#define SERVER_REST_CITY     @"/rest/city"

#define HEADER_ACCEPT        @"Accept"
#define CONTENT_XML          @"application/xml"
#define CONTENT_JSON         @"application/json"
#define DEFAULT_CONTENT      CONTENT_JSON

#define ENTITY_USER          @"User"
#define ENTITY_ROLE          @"Role"

// Entity: User and Role
#define FIELD_ID        @"ID"
#define FIELD_USERNAME  @"Username"
#define FIELD_PASSWORD  @"Password"
#define FIELD_FULLNAME  @"Fullname"
#define FIELD_ENABLED   @"Enabled"
#define FIELD_ROLE      @"Role"
#define FIELD_ROLE_NAME @"Name"

#define MAX_USERNAME    35
#define MAX_PASSWORD    40
#define MAX_FULLNAME    150
#define MAX_ROLE_NAME   20

