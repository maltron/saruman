//
//  Role.h
//  saruman
//
//  Created by Mauricio Leal on 2/25/14.
//  Copyright (c) 2014 Mauricio Leal. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface Role : NSManagedObject

@property (nonatomic, retain) NSNumber * id;
@property (nonatomic, retain) NSString * name;

@end
