//
//  User.m
//  saruman
//
//  Created by Mauricio Leal on 3/16/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "User.h"
#import "Role.h"
#import "Default.h"

@implementation User

@dynamic enabled;
@dynamic fullName;
@dynamic id;
@dynamic password;
@dynamic username;
@dynamic role;

-(BOOL)isAdmin {
    if([self role] == nil) return NO;
    
    return [[self.role name] isEqualToString:ROLE_ADMIN];
}

@end
