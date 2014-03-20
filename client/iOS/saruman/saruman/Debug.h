//
//  Debug.h
//  saruman
//
//  Created by Mauricio Leal on 3/18/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "User.h"

@interface Debug : NSObject

+(NSString *)describeUser:(User *)user;
//+(NSString *)decribeField:(NSString *)fieldName andValue:(NSString *)value;

@end
