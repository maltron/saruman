//
//  Option.m
//  saruman
//
//  Created by Mauricio Leal on 2/28/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "Option.h"

@implementation Option

@synthesize label = _label;
@synthesize view = _view;
@synthesize cellIdentifier = _cellIdentifier;
@synthesize cellStyle = _cellStyle;

-(id)initWithView:(UIView *)view usingIdentifier:(NSString *)identifier withCellStyle:(UITableViewCellStyle)cellStyle {
    self = [super init];
    if(self) {
        _label = nil;
        _view = view;
        _cellIdentifier = identifier;
        _cellStyle = cellStyle;
    }
    
    return self;
}

-(id)initWithLabel:(UILabel *)label andView:(UIView *)view usingIdentifier:(NSString *)identifier withCellStyle:(UITableViewCellStyle)cellStyle {
    self = [super init];
    if(self) {
        _label = label;
        _view = view;
        _cellIdentifier = identifier;
        _cellStyle = cellStyle;
    }
    
    return self;
}

-(BOOL)hasLabel {
    return _label != nil;
}

@end
