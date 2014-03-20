//
//  UserParser.h
//  saruman
//
//  Created by Mauricio Leal on 3/16/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "User.h"

@interface UserParser : NSObject
@property (nonatomic, strong) User *user;

-(id)initWithDictionary:(NSDictionary *)content andContext:(NSManagedObjectContext *)context;
-(NSData *)json;

@end
