//
//  Option.h
//  saruman
//
//  Created by Mauricio Leal on 2/28/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Option : NSObject
@property (nonatomic, strong) UILabel *label;
@property (nonatomic, strong) UIView *view;
@property (nonatomic, strong) NSString *cellIdentifier;
@property (nonatomic, assign) UITableViewCellStyle cellStyle;

-(id)initWithView:(UIView *)view usingIdentifier:(NSString *)identifier withCellStyle:(UITableViewCellStyle)cellStyle;

-(id)initWithLabel:(UILabel *)label andView:(UIView *)view usingIdentifier:(NSString *)identifier withCellStyle:(UITableViewCellStyle)cellStyle;

-(BOOL)hasLabel;

@end
