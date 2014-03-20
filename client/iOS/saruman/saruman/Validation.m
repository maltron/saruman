//
//  Validation.m
//  saruman
//
//  Created by Mauricio Leal on 3/18/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "Validation.h"

@implementation Validation

+(BOOL)notNull:(id)value withClass:(Class)classType {
    if(value == nil) return NO;

    return [value isKindOfClass:(classType)];
}

+(BOOL)null:(id)value withClass:(Class)classType {
    return [value isKindOfClass:(classType)];
}

@end
