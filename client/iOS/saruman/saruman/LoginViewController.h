//
//  LoginViewController.h
//  saruman
//
//  Created by Mauricio Leal on 3/4/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "OptionsViewController.h"
#import "SettingsViewController.h"
#import "UserParser.h"

@interface LoginViewController : OptionsViewController<UITableViewDelegate, NSURLConnectionDataDelegate, UITextFieldDelegate>
@property (nonatomic, strong) UITextField *textUsername;
@property (nonatomic, strong) UITextField *textPassword;
//@property (nonatomic, strong) UIActivityIndicatorView *activityIndicatorView;
@property (nonatomic, strong) UIAlertView *alertView;

@property (nonatomic, strong) UIBarButtonItem *buttonLogin;
@property (nonatomic, strong) UIBarButtonItem *buttonSettings;

@property (nonatomic, strong) SettingsViewController *settingsViewController;

@property (nonatomic, assign) BOOL success;
@property (nonatomic, strong) UserParser *userParser;

@property (nonatomic, strong) NSManagedObjectContext *context;

@end
