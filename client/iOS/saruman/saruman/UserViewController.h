//
//  UserViewController.h
//  saruman
//
//  Created by Mauricio Leal on 3/18/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "OptionsViewController.h"

@interface UserViewController : OptionsViewController
@property (nonatomic, strong) UITextField *textUsername;
@property (nonatomic, strong) UITextField *textPassword;
@property (nonatomic, strong) UITextField *textFullname;
@property (nonatomic, strong) UISwitch *switchEnabled;

@end
