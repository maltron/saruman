//
//  TestingOptionsViewController.m
//  saruman
//
//  Created by Mauricio Leal on 3/1/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "TestingOptionsViewController.h"
#import "TextTool.h"
#import "Option.h"

@interface TestingOptionsViewController ()

@end

@implementation TestingOptionsViewController

-(void)viewDidLoad {
    [super viewDidLoad];
    
    _textName = [TextTool textField];
    [self.textName setPlaceholder:@"Username"];
    
    _textPassword = [TextTool textField];
    [self.textPassword setSecureTextEntry:YES];
    [self.textPassword setPlaceholder:@"Password"];
    
    Option *optionName = [[Option alloc] initWithView:self.textName usingIdentifier:@"cellName" withCellStyle:UITableViewCellStyleDefault];
    Option *optionPassword = [[Option alloc] initWithView:self.textPassword usingIdentifier:@"cellPassword" withCellStyle:UITableViewCellStyleDefault];
    
    self.arrayOptions = [NSArray arrayWithObjects:optionName, optionPassword, nil];
}

@end
