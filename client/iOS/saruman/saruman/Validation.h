//
//  Validation.h
//  saruman
//
//  Created by Mauricio Leal on 3/18/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Validation : NSObject

+(BOOL)notNull:(id)value withClass:(Class)classType;
+(BOOL)null:(id)value withClass:(Class)classType;


@end
