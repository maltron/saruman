//
//  Role.h
//  saruman
//
//  Created by Mauricio Leal on 3/4/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class User;

@interface Role : NSManagedObject

@property (nonatomic, retain) NSNumber *id;
@property (nonatomic, retain) NSString *name;
@property (nonatomic, retain) User *user;

@end
