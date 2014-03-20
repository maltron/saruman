//
//  User.h
//  saruman
//
//  Created by Mauricio Leal on 3/16/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class Role;

@interface User : NSManagedObject

@property (nonatomic, retain) NSNumber * enabled;
@property (nonatomic, retain) NSString * fullName;
@property (nonatomic, retain) NSNumber * id;
@property (nonatomic, retain) NSString * password;
@property (nonatomic, retain) NSString * username;
@property (nonatomic, retain) Role *role;

-(BOOL)isAdmin;

@end
