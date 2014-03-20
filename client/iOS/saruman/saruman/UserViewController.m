//
//  UserViewController.m
//  saruman
//
//  Created by Mauricio Leal on 3/18/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "UserViewController.h"
#import "TextTool.h"
#import "Option.h"

@interface UserViewController ()

@end

@implementation UserViewController

-(void)viewDidLoad {
    [super viewDidLoad];
    
    _textUsername = [TextTool textField];
    [self.textUsername setTranslatesAutoresizingMaskIntoConstraints:NO];
    [self.textUsername setKeyboardType:UIKeyboardTypeEmailAddress];
    
    _textPassword = [TextTool textField];
    [self.textPassword setTranslatesAutoresizingMaskIntoConstraints:NO];
    [self.textPassword setSecureTextEntry:YES];
    
    _textFullname = [TextTool textField];
    [self.textFullname setTranslatesAutoresizingMaskIntoConstraints:NO];
    
    _switchEnabled = [[UISwitch alloc] init];
    [self.switchEnabled setTranslatesAutoresizingMaskIntoConstraints:NO];
    [self.switchEnabled setOn:YES];
    
    Option *optionUsername = [[Option alloc] initWithLabel:[TextTool label:@"Username"]
        andView:self.textUsername usingIdentifier:@"cellUsername"
                                             withCellStyle:UITableViewCellStyleDefault];
    Option *optionPassword = [[Option alloc] initWithLabel:[TextTool label:@"Password"]
        andView:self.textPassword usingIdentifier:@"cellPassword"
                                             withCellStyle:UITableViewCellStyleDefault];
    Option *optionFullname = [[Option alloc] initWithLabel:[TextTool label:@"Fullname"]
        andView:self.textFullname usingIdentifier:@"cellFullname"
                                             withCellStyle:UITableViewCellStyleDefault];
    Option *optionEnabled = [[Option alloc] initWithLabel:[TextTool label:@"Enabled"]
        andView:self.switchEnabled usingIdentifier:@"cellEnabled"
                                             withCellStyle:UITableViewCellStyleDefault];
    
    self.arrayOptions = [NSArray arrayWithObjects:optionUsername, optionPassword, optionFullname, optionEnabled, nil];
    [self scanForReference];
}

@end
