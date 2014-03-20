//
//  Constraints.m
//  saruman
//
//  Created by Mauricio Leal on 3/4/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "Constraints.h"

@implementation Constraints

+(NSMutableArray *)center:(UIView *)view {
    NSMutableArray *constraints = [[NSMutableArray alloc] init];
    // Fill the content with the table
    [constraints addObject:[NSLayoutConstraint
    constraintWithItem:view attribute:NSLayoutAttributeWidth
    relatedBy:NSLayoutRelationEqual
    toItem:view.superview attribute:NSLayoutAttributeWidth
                                multiplier:1.0f constant:0.0f]];
    
    [constraints addObject:[NSLayoutConstraint
    constraintWithItem:view attribute:NSLayoutAttributeHeight
    relatedBy:NSLayoutRelationEqual
    toItem:view.superview attribute:NSLayoutAttributeHeight
                                multiplier:1.0f constant:0.0f]];
    
    // Now, center it out
    [constraints addObject:[NSLayoutConstraint
    constraintWithItem:view attribute:NSLayoutAttributeCenterX
    relatedBy:NSLayoutRelationEqual
    toItem:view.superview attribute:NSLayoutAttributeCenterX
                            multiplier:1.0f constant:0.0f]];
    [constraints addObject:[NSLayoutConstraint
    constraintWithItem:view attribute:NSLayoutAttributeCenterY
    relatedBy:NSLayoutRelationEqual
    toItem:view.superview attribute:NSLayoutAttributeCenterY
                            multiplier:1.0f constant:0.0f]];
    
    return constraints;
}

+(NSMutableArray *)centerCell:(UIView *)view {
    NSMutableArray *constraints = [[NSMutableArray alloc] init];
    
    // Set the same Width and add 10px on either side
    [constraints addObject:[NSLayoutConstraint
    constraintWithItem:view attribute:NSLayoutAttributeWidth
    relatedBy:NSLayoutRelationEqual
    toItem:view.superview attribute:NSLayoutAttributeWidth
                            multiplier:1.0f constant:-10.0f]];
    // Now Center
    [constraints addObject:[NSLayoutConstraint
    constraintWithItem:view attribute:NSLayoutAttributeCenterX
    relatedBy:NSLayoutRelationEqual
    toItem:view.superview attribute:NSLayoutAttributeCenterX
                            multiplier:1.0f constant:0.0f]];
    
    [constraints addObject:[NSLayoutConstraint
    constraintWithItem:view attribute:NSLayoutAttributeCenterY
    relatedBy:NSLayoutRelationEqual
    toItem:view.superview attribute:NSLayoutAttributeCenterY
                            multiplier:1.0f constant:0.0f]];
    
    return constraints;
}

+(NSMutableArray *)centerCell:(Option *)option withLabelWidth:(CGFloat)width {
    NSMutableArray *constraints = [[NSMutableArray alloc] init];
    
    // Add Label (if any)
    if([option hasLabel]) {
        [constraints addObject:[NSLayoutConstraint
        constraintWithItem:[option label] attribute:NSLayoutAttributeLeading
        relatedBy:NSLayoutRelationEqual
        toItem:[[option label] superview] attribute:NSLayoutAttributeLeading
                                multiplier:1.0f constant:5.0f]];
        
        if(width > 0) {
            [constraints addObject:[NSLayoutConstraint
            constraintWithItem:[option label] attribute:NSLayoutAttributeWidth
            relatedBy:NSLayoutRelationEqual
            toItem:nil attribute:NSLayoutAttributeNotAnAttribute
                                multiplier:1.0f constant:width]];
        }
        
        
        [constraints addObject:[NSLayoutConstraint
        constraintWithItem:[option label] attribute:NSLayoutAttributeCenterY
        relatedBy:NSLayoutRelationEqual
        toItem:[[option label] superview] attribute:NSLayoutAttributeCenterY
                                multiplier:1.0f constant:0.0f]];
    }

    // Fill the rest of space with the view component
    [constraints addObject:[NSLayoutConstraint
    constraintWithItem:[option view] attribute:NSLayoutAttributeLeading
    relatedBy:NSLayoutRelationEqual
    toItem:[option label] attribute:NSLayoutAttributeTrailing
                            multiplier:1.0f constant:5.0f]];

    [constraints addObject:[NSLayoutConstraint
    constraintWithItem:[option view] attribute:NSLayoutAttributeCenterY
    relatedBy:NSLayoutRelationEqual
    toItem:[[option view] superview] attribute:NSLayoutAttributeCenterY
                            multiplier:1.0f constant:0.0f]];
    
    [constraints addObject:[NSLayoutConstraint
    constraintWithItem:[option view] attribute:NSLayoutAttributeWidth
    relatedBy:NSLayoutRelationGreaterThanOrEqual
    toItem:nil attribute:NSLayoutAttributeNotAnAttribute
                            multiplier:1.0f constant:100.0f]];
    
    [constraints addObject:[NSLayoutConstraint
    constraintWithItem:[option view] attribute:NSLayoutAttributeTrailing
    relatedBy:NSLayoutRelationEqual
    toItem:[[option view] superview] attribute:NSLayoutAttributeTrailing
                                multiplier:1.0f constant:-5.0f]];
    
    return constraints;
}

@end
