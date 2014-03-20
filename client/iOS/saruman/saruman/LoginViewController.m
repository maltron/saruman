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
#import "AppDelegate.h"
#import "Default.h"
#import "User.h"
#import "Role.h"
#import "Debug.h"

#import "UserViewController.h"

@interface LoginViewController ()

@end

@implementation LoginViewController

-(void)viewDidLoad {
    [super viewDidLoad];
    
    [self.navigationItem setTitle:@"SARUMAN"];
    
    _buttonLogin = [[UIBarButtonItem alloc] initWithTitle:@"Login"
        style:UIBarButtonItemStyleBordered target:self action:@selector(login:)];
    [self.navigationItem setRightBarButtonItem:self.buttonLogin];
    
    _buttonSettings = [[UIBarButtonItem alloc] initWithTitle:@"Settings"
        style:UIBarButtonItemStyleBordered target:self action:@selector(settings:)];
    [self.navigationItem setLeftBarButtonItem:self.buttonSettings];
    
    _textUsername = [TextTool textField];
    [self.textUsername setPlaceholder:@"Username"];
    [self.textUsername setTranslatesAutoresizingMaskIntoConstraints:NO];
    [self.textUsername setKeyboardType:UIKeyboardTypeEmailAddress];
    [self.textUsername setDelegate:self];
    // Later, this Control will be added into cell.contentView
    
    // Is there a sucessfully User logged in ?
    NSString *lastUsername = [[NSUserDefaults standardUserDefaults]
                              stringForKey:IDENTIFER_CURRENT_USER];
    if(lastUsername) [self.textUsername setText:lastUsername];
    
    _textPassword = [TextTool textField];
    [self.textPassword setPlaceholder:@"Password"];
    [self.textPassword setTranslatesAutoresizingMaskIntoConstraints:NO];
    [self.textPassword setSecureTextEntry:YES];
    [self.textPassword setDelegate:self];
    
    // Later, this Control will be added into cell.contentView
    
    
//    UILabel *labelUsername = [TextTool label:@"AAAAAA"];
//    UILabel *labelPassword = [TextTool label:@"BB"];
//    Option *optionUsername = [[Option alloc] initWithLabel:labelUsername
//        andView:self.textUsername usingIdentifier:@"cellUsername"
//                            withCellStyle:UITableViewCellStyleDefault];
//    Option *optionPassword = [[Option alloc] initWithLabel:labelPassword
//        andView:self.textPassword usingIdentifier:@"cellPassword"
//                            withCellStyle:UITableViewCellStyleDefault];
    
    Option *optionUsername = [[Option alloc]
        initWithView:self.textUsername usingIdentifier:@"cellUsername"
                                withCellStyle:UITableViewCellStyleDefault];
    Option *optionPassword = [[Option alloc]
        initWithView:self.textPassword usingIdentifier:@"cellPassword"
                              withCellStyle:UITableViewCellStyleDefault];
    
    self.arrayOptions = [NSArray arrayWithObjects:optionUsername, optionPassword, nil];
    [self scanForReference];
}

#pragma Business Methods

-(void)settings:(id)sender {
    if(_settingsViewController == nil)
        _settingsViewController = [[SettingsViewController alloc] init];
    
    [self.navigationController pushViewController:_settingsViewController animated:YES];
}

-(void)login:(id)sender {
    NSLog(@"login");
    [self.textUsername resignFirstResponder];
    [self.textPassword resignFirstResponder];
    
    // Is there both information to perform Login ?
    if([[self.textUsername text] length] == 0 ||
       [[self.textPassword text] length] == 0) {
        [self displayAlertView:@"Error" withMessage:@"Needs both Username and Password to Login"];
        return;
    }
    
    [self.buttonLogin setEnabled:NO];
    AppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:
                                    [appDelegate requestUsername:[self.textUsername text]]];
    [request setHTTPMethod:@"GET"];
    [request setValue:DEFAULT_CONTENT forHTTPHeaderField:HEADER_ACCEPT];
    
    [NSURLConnection connectionWithRequest:request delegate:self];
}

-(BOOL)matchPassword:(User *)user {
    if([[user password] isEqualToString:[self.textPassword text]]) {
        // Is this User Enabled ????
        if(![user enabled]) {
            [self displayAlertView:@"Error: Disabled" withMessage:@"You account is Disabled"];
            return NO;
        }
        
        // YES, the password matches. Then save it for future references
        NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
        [defaults setObject:[user username] forKey:IDENTIFER_CURRENT_USER];
        [defaults setObject:[[user role] name] forKey:IDENTIFER_CURRENT_ROLE];
        
        // Keep a reference of this user in the Delegate
        AppDelegate *delegate = [[UIApplication sharedApplication] delegate];
        [delegate setCurrentUser:user];
        
        
    } else {
        // NO....This User is not allowed to go further
        [self displayAlertView:@"Error: Not Authorized"
                   withMessage:@"You're not authorized to go any further"];
        return NO;
    }
    
    // PENDING: After successfull login, go to other UIViewController
    
    // TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP
    UserViewController *userViewController = [[UserViewController alloc] init];
    [self.navigationController pushViewController:userViewController animated:YES];
        
    return YES;
}

#pragma URL Connection DataDelegate
-(void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response {
    NSLog(@"didReceiveResponse");
    // Enable Login Button again
    [self.buttonLogin setEnabled:YES];

    NSHTTPURLResponse *httpResponse = (NSHTTPURLResponse *)response;
    self.success = ([httpResponse statusCode] == 200);
    
    // Not Successfull ?
    if(!self.success)
        [self displayAlertView:[NSString stringWithFormat:@"Erro:%d",[httpResponse statusCode]]
                   withMessage:[NSString stringWithFormat:@"Unable to Authenticate User:%@",
                                                                    [self.textUsername text]]];
}

-(void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
    NSLog(@"didReceiveData");
    
    NSError *error = nil;
    
    if(self.success) {
        NSString *output = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
        NSLog(@"didReceiveData:%@", output);
        
        NSDictionary *result = [NSJSONSerialization JSONObjectWithData:data
                            options:NSJSONReadingMutableContainers error:&error];
        
        // Is there a problem with JSON Content ?
        if(error)
            [self displayAlertView:[NSString stringWithFormat:@"Error: Wrong JSON"]
                       withMessage:@"The content coming from Server has errors"];
        else {
            
            // No, try to parse it into the Object User
            @try {
                self.userParser = [[UserParser alloc] initWithDictionary:result andContext:self.context];
                // Check the password
                [self matchPassword:[self.userParser user]];
            }
            @catch (NSException *exception) {
                NSLog(@"### NSException:%@", [exception name]);
            }
            
//            User *currentUser = [self.userParser user];
//            NSLog(@"didReceiveData User Logged:%@", [Debug describeUser:currentUser]);
//            
//            // Serialize into JSON
//            NSLog(@"didReceiveData SERIALIZATION");
//            NSLog(@"didReceiveData:%@", [[NSString alloc] initWithData:[self.userParser json] encoding:NSUTF8StringEncoding]);
        }
        
    } else {
        // ERROR: Another code was retrieve from the result of the transaction
    }
    
    
}

#pragma TextField Delegate

-(BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range
                                                replacementString:(NSString *)string {
    NSUInteger length = [textField.text length] + [string length] - range.length;
    if(textField == self.textUsername)
        return (length > MAX_USERNAME ? NO : YES);
    else if(textField == self.textPassword)
        return (length > MAX_PASSWORD ? NO : YES);
    
    return NO;
}

#pragma UITableView DATASOURCE

-(NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    return @"S.A. Resources Management";
}

#pragma Lazy instantiation of certain Components

-(void)displayAlertView:(NSString *)title withMessage:(NSString *)message {
    if(self.alertView == nil)
        self.alertView = [[UIAlertView alloc] initWithTitle:title message:message
                delegate:nil cancelButtonTitle:nil otherButtonTitles:@"Ok", nil];
    else {
        [self.alertView setTitle:title];
        [self.alertView setMessage:message];
    }
    
    [self.alertView show];
}



@end
