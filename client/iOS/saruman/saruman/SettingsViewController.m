//
//  SettingsViewController.m
//  saruman
//
//  Created by Mauricio Leal on 3/4/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "AppDelegate.h"
#import "SettingsViewController.h"
#import "TextTool.h"
#import "Option.h"
#import "Default.h"

@interface SettingsViewController ()

@end

@implementation SettingsViewController

// TODO: Ping to Server
// TODO: Ping to DataBase
// TODO: Password change

-(void)viewDidLoad {
    [super viewDidLoad];
    
    _labelServer = [TextTool label:@"Server"];
    [self.view addSubview:self.labelServer];
    
    _textServer = [TextTool textField];
    [self.view addSubview:self.textServer];
    
    _labelPort = [TextTool label:@"Port"];
    [self.view addSubview:self.labelPort];
    
    _textPort = [TextTool textField];
    [self.view addSubview:self.textPort];
    
    // Set the values for each component
    AppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
    [self.textServer setText:[appDelegate server]];
    
    
    // Defaults
    Option *optionServer = [[Option alloc] initWithLabel:self.labelServer andView:self.textServer usingIdentifier:@"cellServer" withCellStyle:UITableViewCellStyleDefault];
    Option *optionPort = [[Option alloc] initWithLabel:self.labelPort andView:self.textPort usingIdentifier:@"cellPort" withCellStyle:UITableViewCellStyleDefault];
    self.arrayOptions = [NSArray arrayWithObjects:optionServer, optionPort, nil];
    [self scanForReference];
}

-(NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    return @"Server's Address and Port";
}



@end
