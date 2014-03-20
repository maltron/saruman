//
//  Constraints.h
//  saruman
//
//  Created by Mauricio Leal on 3/4/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Option.h"

@interface Constraints : NSObject

+(NSMutableArray *)center:(UIView *)view;
+(NSMutableArray *)centerCell:(UIView *)view;
+(NSMutableArray *)centerCell:(Option *)option withLabelWidth:(CGFloat)width;

@end
