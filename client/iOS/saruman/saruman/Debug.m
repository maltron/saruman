//
//  Debug.m
//  saruman
//
//  Created by Mauricio Leal on 3/18/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "Debug.h"
#import "User.h"
#import "Role.h"

#import "Default.h"

@implementation Debug

+(NSString *)describeUser:(User *)user {
    NSMutableString *value = [[NSMutableString alloc] init];
    [value appendString:[self describeField:FIELD_ID andValue:[[user id] stringValue]]];
    [value appendString:[self describeField:FIELD_USERNAME andValue:[user username]]];
    [value appendString:[self describeField:FIELD_PASSWORD andValue:[user password]]];
    [value appendString:[self describeField:FIELD_FULLNAME andValue:[user fullName]]];
    [value appendString:[self describeField:FIELD_ENABLED andValue:([user enabled] ? @"YES" : @"NO")]];
    
    if([user role] != nil) {
        [value appendString:@"Role "];
        [value appendString:[self describeField:FIELD_ID andValue:[[user.role id] stringValue]]];
        [value appendString:[self describeField:FIELD_ROLE_NAME andValue:[user.role name]]];
    } else {
        [value appendString:@"Role IS NULL"];
    }
    
     return [value stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
}

+(NSString *)describeField:(NSString *)fieldName andValue:(NSString *)value {
    return [[[[NSString stringWithString:fieldName]
             stringByAppendingString:@":"]
                stringByAppendingString:value]
                        stringByAppendingString:@" "];
}

@end
