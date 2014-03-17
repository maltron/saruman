//
//  OptionsViewController.h
//  saruman
//
//  Created by Mauricio Leal on 2/28/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface OptionsViewController : UIViewController<UITableViewDataSource>
@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *arrayOptions;

@property (nonatomic, copy) UILabel *labelReference;

-(void)scanForReference;

@end
