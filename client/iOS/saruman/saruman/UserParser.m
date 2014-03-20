//
//  UserParser.m
//  saruman
//
//  Created by Mauricio Leal on 3/16/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "UserParser.h"
#import "Default.h"
#import "Role.h"
#import "Validation.h"

@implementation UserParser
@synthesize user = _user;

-(id)initWithDictionary:(NSDictionary *)content andContext:(NSManagedObjectContext *)context {
    self = [super init];
    if(self) {
        //_user = [[User alloc] init];
        _user = [NSEntityDescription insertNewObjectForEntityForName:ENTITY_USER
                                              inManagedObjectContext:context];
        
        // ID (NOT NULL)
        id value = [content objectForKey:FIELD_ID];
        if([Validation notNull:value withClass:[NSNumber class]])
            [self.user setId:value];
        else @throw [NSException exceptionWithName:@"ParserException"
                        reason:@"ID is either empty or not compatible" userInfo:nil];
        
        // Username (NOT NULL)
        value = [content objectForKey:FIELD_USERNAME];
        if([Validation notNull:value withClass:[NSString class]])
            [self.user setUsername:value];
        else @throw [NSException exceptionWithName:@"ParserException"
                reason:@"Username is either empty or not compatible" userInfo:nil];
        
        // Password (NOT NULL)
        value = [content objectForKey:FIELD_PASSWORD];
        if([Validation notNull:value withClass:[NSString class]])
            [self.user setPassword:value];
        else @throw [NSException exceptionWithName:@"ParserException"
                    reason:@"Password is either empty or not compatible" userInfo:nil];
        
        
        // Fullname (NULL)
        value = [content objectForKey:FIELD_FULLNAME];
        if([Validation null:value withClass:[NSString class]])
            [self.user setFullName:value];
        else @throw [NSException exceptionWithName:@"ParserException"
                    reason:@"FullName is not compatible" userInfo:nil];
        
        
        // Enabled (NOT NULL)
        value = [content objectForKey:FIELD_ENABLED];
        if([value boolValue])
            [self.user setEnabled:value];
        else @throw [NSException exceptionWithName:@"ParserException"
                reason:@"Enabled is either empty or not compatible" userInfo:nil];
        
        // Role (NOT NULL)
        value = [content objectForKey:FIELD_ROLE];
        if(value != nil && [value isKindOfClass:([NSDictionary class])]) {
            NSDictionary *roleDictionary = (NSDictionary *)value;
            // It seems, without that particular part (creating the Role Entity)
            // it's not possible to instantiate the Role
            _user.role = [NSEntityDescription insertNewObjectForEntityForName:ENTITY_ROLE
                                                    inManagedObjectContext:context];
            //NSLog(@">>> Processing Role ID:%@ Name:%@", [roleDictionary objectForKey:FIELD_ID],
            //      [roleDictionary objectForKey:FIELD_ROLE_NAME]);
            
            // ID
            value = [roleDictionary objectForKey:FIELD_ID];
            if([Validation notNull:value withClass:[NSNumber class]])
                [_user.role setId:value];
            else @throw [NSException exceptionWithName:@"ParserException"
                reason:@"Role.ID is either empty or not compatible" userInfo:nil];
            
            // Name
            value = [roleDictionary objectForKey:FIELD_ROLE_NAME];
            if([Validation notNull:value withClass:[NSString class]])
                [_user.role setName:value];
            else @throw [NSException exceptionWithName:@"ParserException"
                reason:@"Role.Name is either empty or not compatible" userInfo:nil];
            
            //NSLog(@">>> Processing Role ID:%@ Name:%@", [_user.role id], [_user.role name]);
        }
    }
    
    return self;
}

-(NSData *)json {
    if(self.user == nil) return nil;
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
    
    if([self.user id] != nil)
        [dictionary setObject:[self.user id] forKey:FIELD_ID];
    
    if([self.user username] != nil)
        [dictionary setObject:[self.user username] forKey:FIELD_USERNAME];
    
    if([self.user password] != nil)
        [dictionary setObject:[self.user password] forKey:FIELD_PASSWORD];
    
    if([self.user fullName] != nil)
        [dictionary setObject:[self.user fullName] forKey:FIELD_FULLNAME];
    
    [dictionary setObject:[self.user enabled] forKey:FIELD_ENABLED];
    
    // Role ?
    if([self.user role] != nil) {
        NSMutableDictionary *dictionaryRole = [[NSMutableDictionary alloc] init];
        
        if([self.user.role id] != nil)
            [dictionaryRole setObject:[self.user.role id] forKey:FIELD_ID];
        if([self.user.role name] != nil)
            [dictionaryRole setObject:[self.user.role name] forKey:FIELD_ROLE_NAME];
        
        [dictionary setObject:dictionaryRole forKey:FIELD_ROLE];
    }
    
    NSError *error = nil;
    
    // Generate the content
    NSData *data = [NSJSONSerialization dataWithJSONObject:dictionary
                                                   options:NSJSONWritingPrettyPrinted error:&error];
    
    if(error) @throw [NSException exceptionWithName:[error localizedDescription]
                                            reason:[error localizedFailureReason] userInfo:[error userInfo]];
    
    return data;
}

@end
