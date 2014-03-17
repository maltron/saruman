//
//  LoginViewController.m
//  saruman
//
//  Created by Mauricio Leal on 3/4/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "LoginViewController.h"
#import "TextTool.h"
#import "Option.h"

@interface LoginViewController ()

@end

@implementation LoginViewController

-(void)viewDidLoad {
    [super viewDidLoad];
    
    [self.navigationItem setTitle:@"SARUMAN"];
    
    _buttonLogin = [[UIBarButtonItem alloc] initWithTitle:@"Login" style:UIBarButtonItemStyleBordered target:nil action:nil];
    [self.navigationItem setRightBarButtonItem:self.buttonLogin];
    _buttonSettings = [[UIBarButtonItem alloc] initWithTitle:@"Settings" style:UIBarButtonItemStyleBordered target:self action:@selector(settings:)];
    [self.navigationItem setLeftBarButtonItem:self.buttonSettings];
    
    _textUsername = [TextTool textField];
    [self.textUsername setPlaceholder:@"Username"];
    [self.textUsername setTranslatesAutoresizingMaskIntoConstraints:NO];
    // Later, this Control will be added into cell.contentView
    
    _textPassword = [TextTool textField];
    [self.textPassword setPlaceholder:@"Password"];
    [self.textPassword setTranslatesAutoresizingMaskIntoConstraints:NO];
    [self.textPassword setSecureTextEntry:YES];
    // Later, this Control will be added into cell.contentView
    
    UILabel *labelUsername = [TextTool label:@"Username"];
    UILabel *labelPassword = [TextTool label:@"Password"];
    
    Option *optionUsername = [[Option alloc] initWithLabel:labelUsername andView:self.textUsername usingIdentifier:@"cellUsername" withCellStyle:UITableViewCellStyleDefault];
    Option *optionPassword = [[Option alloc] initWithLabel:labelPassword andView:self.textPassword usingIdentifier:@"cellPassword" withCellStyle:UITableViewCellStyleDefault];
    
    self.arrayOptions = [NSArray arrayWithObjects:optionUsername, optionPassword, nil];
    //[self scanForReference];
}

#pragma Business Methods

-(void)settings:(id)sender {
    if(_settingsViewController == nil)
        _settingsViewController = [[SettingsViewController alloc] init];
    
    [self.navigationController pushViewController:_settingsViewController animated:YES];
}


#pragma UITableView DATASOURCE

-(NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    return @"S.A. Resources Management";
}



@end
