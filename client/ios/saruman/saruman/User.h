//
//  User.h
//  saruman
//
//  Created by Mauricio Leal on 2/25/14.
//  Copyright (c) 2014 Mauricio Leal. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface User : NSManagedObject

@property (nonatomic, retain) UNKNOWN_TYPE id;
@property (nonatomic, retain) UNKNOWN_TYPE username;
@property (nonatomic, retain) UNKNOWN_TYPE password;
@property (nonatomic, retain) UNKNOWN_TYPE fullName;
@property (nonatomic, retain) UNKNOWN_TYPE enabled;
@property (nonatomic, retain) NSManagedObject *role;

@end
