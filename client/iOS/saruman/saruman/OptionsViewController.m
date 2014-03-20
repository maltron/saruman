//
//  OptionsViewController.m
//  saruman
//
//  Created by Mauricio Leal on 2/28/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "OptionsViewController.h"
#import "Option.h"
#import "Constraints.h"

@interface OptionsViewController ()

@end

@implementation OptionsViewController

#pragma mark VIEW CONTROLLER

-(void)viewDidLoad {
    [super viewDidLoad];
    
    _tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStyleGrouped];
    [self.tableView setTranslatesAutoresizingMaskIntoConstraints:NO];
    [self.tableView setDataSource:self];
    // Disable all kind of Selection
    [self.tableView setAllowsSelection:NO];
    [self.tableView setAllowsSelectionDuringEditing:NO];
    [self.tableView setAllowsMultipleSelection:NO];
    [self.tableView setAllowsMultipleSelectionDuringEditing:NO];
    
    [self.view addSubview:self.tableView];
        
    [self.view addConstraints:[Constraints center:self.tableView]];
}

// Check if in the arrayOptions, there is an UILabel to be used
// as a reference
-(void)scanForReference {
    if([self.arrayOptions count] == 0) {
        NSLog(@"### OptionsViewController: ArrayOptions HAS NO ELEMENTS");
        return;
    }
    
    // Check for which Label is the widest
    
    for(Option *option in self.arrayOptions) {
        if([option label] == nil) break; // Assuming all labels are nil
        
        // Get the first reference
        if(self.widthReference == 0) {
            self.widthReference = [self sizeBasedOnFont:[option label]].width;
            continue;
        }
        
        if([self sizeBasedOnFont:[option label]].width > self.widthReference)
            self.widthReference = [self sizeBasedOnFont:[option label]].width;
    }
}

//-(CGFloat)max:(UILabel *)label {
//    
//    CGSize size;
//    if([[label text] respondsToSelector:@selector(boundingRectWithSize:options:attributes:context:)]) {
//        NSLog(@"iOS 7 Selector: boundingRectWithSize:options:attributes:context:");
//        size = [[label text]
//                boundingRectWithSize:CGSizeMake(label.frame.size.width, CGFLOAT_MAX)
//                options:NSStringDrawingUsesLineFragmentOrigin
//                attributes:@{NSFontAttributeName:label.font} context:nil].size;
//    } else {
//        NSLog(@"iOS 6 Selector: sizeWithFont");
//        size = [[label text] sizeWithFont:label.font];
//    }
//    
//    return size.width;
//}

-(CGSize)sizeBasedOnFont:(UILabel *)label {
    if([[label text] respondsToSelector:@selector(boundingRectWithSize:options:attributes:context:)])
        // iOS 7 Only
        return [[label text]
                boundingRectWithSize:CGSizeMake(label.frame.size.width, CGFLOAT_MAX)
                options:NSStringDrawingUsesLineFragmentOrigin
                attributes:@{NSFontAttributeName:label.font} context:nil].size;
    
    
    // iOS 6.x (Deprecated)
    return [[label text] sizeWithFont:[label font]];
}

#pragma mark UITableView DATASOURCE 

-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.arrayOptions count];
}

-(UITableViewCell *)tableView:(UITableView *)aTableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    Option *option = [self.arrayOptions objectAtIndex:indexPath.row];
    UITableViewCell *cell = [aTableView dequeueReusableCellWithIdentifier:[option cellIdentifier]];
    if(cell == nil)
        cell = [[UITableViewCell alloc] initWithStyle:[option cellStyle] reuseIdentifier:[option cellIdentifier]];
    
    // It's important the component be ready to be used on AutoLayout
    if([option hasLabel]) [[option label] setTranslatesAutoresizingMaskIntoConstraints:NO];
    [[option view] setTranslatesAutoresizingMaskIntoConstraints:NO];
    
    // Add the component into the Cell's Content
    if([option hasLabel]) [cell.contentView addSubview:[option label]];
    [cell.contentView addSubview:[option view]];
    
    [cell.contentView addConstraints:[Constraints centerCell:option withLabelWidth:self.widthReference]];

    return cell;
}

@end
