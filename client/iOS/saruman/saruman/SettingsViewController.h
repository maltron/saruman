//
//  SettingsViewController.h
//  saruman
//
//  Created by Mauricio Leal on 3/4/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "OptionsViewController.h"

@interface SettingsViewController : OptionsViewController
@property (nonatomic, strong) UILabel *labelServer;
@property (nonatomic, strong) UILabel *labelPort;
@property (nonatomic, strong) UITextField *textServer;
@property (nonatomic, strong) UITextField *textPort;

@end
